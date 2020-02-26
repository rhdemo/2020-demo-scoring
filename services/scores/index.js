'use strict';
const log = require('../../utils/log')('scoring-service');
const {kafkaProducer, KAFKA_TRANSACTION_TOPIC} = require('../../kafka/producer');

module.exports = async function (fastify, opts) {
  fastify.post('/scores', async function (request, reply) {
    log.info('score received');
    let guess = request.body;
    let {game, player, item, answers, pointsAvailable} = guess;

    if (!game || !player || !item || !answers || !Number.isInteger(pointsAvailable)) {
      log.warn(`Ignoring incoming malformed guess data.`);
      return reply
        .code(400)
        .type('application/json')
        .send({ message: 'Malformed request' });
    }

    answers.forEach((currentDigit, index) => {
      if (currentDigit.format === 'decimal' || currentDigit.result === 'correct') {
        return; // skip, no need to answer
      }

      if ((currentDigit.result && currentDigit.result !== 'pending') || !Number.isInteger(currentDigit.number)) {
        return; // skip, no attempt to answer
      }

      if (currentDigit.number === item.price[index]) {
        currentDigit.result = 'correct';
      } else {
        currentDigit.result = 'incorrect';
        if (pointsAvailable > 0) {
          pointsAvailable = pointsAvailable - 5;
        }
      }
    });

    let correct = true;
    for (let i = 0; i < answers.length; i++) {
      let a = answers[i];
      if (a.format === 'number' && a.result !== 'correct') {
        correct = false;
      }
    }

    let result;
    if (correct) {
      result = {answers, pointsAvailable: 0, points: pointsAvailable, correct}
    } else {
      result = {answers, pointsAvailable, points: 0, correct}
    }

    sendKafkaMsg({game, player, item, transaction: result});
    return result;
  });
};


function sendKafkaMsg({game, player, item, transaction}) {
  let jsonMsg = JSON.stringify({game, player, item, transaction});

  let kafkaMsg = Buffer.from(jsonMsg);

  log.info(`kafka produce topic: ${KAFKA_TRANSACTION_TOPIC}; key: null; msg: ${jsonMsg}`);

  try {
    let result = kafkaProducer.produce(KAFKA_TRANSACTION_TOPIC, -1, kafkaMsg, null);
    log.info('kafka producer sent guess transaction payload', result, jsonMsg);
  } catch (error) {
    log.error('kafka producer failed to send guess transaction payload.  Error: ', error.message);
  }
}