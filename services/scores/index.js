'use strict';
const log = require('../../utils/log')('scoring-service');
const Player = require('../../models/player');
const {kafkaProducer, KAFKA_TRANSACTION_TOPIC} = require('../../kafka/producer');

module.exports = async function (fastify, opts) {
  fastify.post('/scores', async function (request, reply) {
    log.debug('scores request body', request.body);
    let guess = request.body;

    if (!guess.player) {
      log.warn('Missing Player information');
      return reply
        .code(400)
        .type('application/json')
        .send({ message: 'Malformed request' });
    }

    if (!guess.answers) {
      // New player needs initialized 1st Round
      let player = new Player(guess.player);
      log.debug('init player round', {player});
      return {player};
    }

    // Existing player adjusted for new answers
    let player = new Player(guess.player);
    player.addGuess(guess.answers);
    sendKafkaMsg(formatKafkaMsg(player));
    log.debug('return-scores', {player});
    return {player};
  });
};

function formatKafkaMsg(player) {
  let {gameId, id, username, score, right, wrong, avatar, creationServer, gameServer, scoringServer} = player;
  const msg = {
    player: {
      gameId,
      id,
      username,
      score,
      right,
      wrong,
      avatar,
      creationServer, // can have duplicate user names on different clusters
      gameServer, // current game server
      scoringServer
    }
  };
  return JSON.stringify(msg);
}

function sendKafkaMsg(jsonMsg) {
  let kafkaMsg = Buffer.from(jsonMsg);

  log.debug(`kafka produce topic: ${KAFKA_TRANSACTION_TOPIC}; key: null; msg: ${jsonMsg}`);

  try {
    let result = kafkaProducer.produce(KAFKA_TRANSACTION_TOPIC, -1, kafkaMsg, null);
    log.debug('kafka producer sent guess transaction payload', result, jsonMsg);
  } catch (error) {
    log.error('kafka producer failed to send guess transaction payload.  Error: ', error.message);
  }
}