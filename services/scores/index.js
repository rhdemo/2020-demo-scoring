'use strict';
const log = require('../../utils/log')('scoring-service');
const Player = require('../../models/player');
const {kafkaProducer, KAFKA_TRANSACTION_TOPIC} = require('../../kafka/producer');

module.exports = async function (fastify, opts) {
  fastify.post('/scores', async function (request, reply) {
    log.debug('scores request body', request.body);
    const startTime = new Date();
    let guess = request.body;

    if (!guess.player) {
      log.warn('Missing Player information');
      return reply
        .code(400)
        .type('application/json')
        .send({ message: 'Malformed request' });
    }

    log.info(`received score from ${guess.player.id}`);

    if (!guess.answers) {
      // New player needs initialized 1st Round
      let player = new Player(guess.player);
      log.debug('init player round', {player});
      const endTime = new Date();
      const timeDiff = endTime - startTime;

      if (timeDiff > 100) {
        log.warn(`Scoring took ${timeDiff} ms`);
      }
      return {player};
    }

    // Existing player adjusted for new answers
    let player = new Player(guess.player);
    player.addGuess(guess.answers);
    sendKafkaMsg(player.id, formatKafkaMsg(player));
    log.debug('return-scores', {player});

    const endTime = new Date();
    const timeDiff = endTime - startTime;

    if (timeDiff > 100) {
      log.warn(`Scoring took ${timeDiff} ms`);
    }

    log.debug('scores response', {player});
    return {player};
  });
};

function formatKafkaMsg(player) {
  let {gameId, id, username, score, avatar, creationServer, gameServer, scoringServer} = player;
  const msg = {
    player: {
      gameId,
      id,
      username,
      score: score.score,
      right: score.right,
      wrong: score.wrong,
      avatar,
      creationServer, // can have duplicate user names on different clusters
      gameServer, // current game server
      scoringServer
    }
  };
  return JSON.stringify(msg);
}

function sendKafkaMsg(key, jsonMsg) {
  let kafkaMsg = Buffer.from(jsonMsg);

  log.info(`kafka produce topic: ${KAFKA_TRANSACTION_TOPIC}; key: ${key}`);
  log.debug(`kafka produce topic: ${KAFKA_TRANSACTION_TOPIC}; key: ${key}; msg: ${jsonMsg}`);

  const startTime = new Date();

  try {
    let result = kafkaProducer.produce(KAFKA_TRANSACTION_TOPIC, -1, kafkaMsg, key);
    log.debug('kafka producer sent guess transaction payload', result, jsonMsg);
  } catch (error) {
    log.error('kafka producer failed to send guess transaction payload.  Error: ', error);
  }

  const endTime = new Date();
  const timeDiff = endTime - startTime;

  if (timeDiff > 100) {
    log.warn(`Kafka message send request took ${timeDiff} ms`);
  }
}