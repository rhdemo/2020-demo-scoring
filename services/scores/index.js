'use strict';
const log = require('../../utils/log')('game-service');

module.exports = async function (fastify, opts) {
  fastify.post('/scores', async function (request, reply) {
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
      if (currentDigit.format === "decimal" || currentDigit.result === "correct") {
        return; // skip, no need to answer
      }

      if ((currentDigit.result && currentDigit.result !== "pending") || !Number.isInteger(currentDigit.number)) {
        return; // skip, no attempt to answer
      }

      if (currentDigit.number === item.price[index]) {
        currentDigit.result = "correct";
      } else {
        currentDigit.result = "incorrect";
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

    //kafka send result {game, player, item , answers, pointsAvailable: 0, points: pointsAvailable, correct}
    return result;
  });
};
