const {CLUSTER_NAME} = require('../utils/constants');
const itemData = require('./item-data');

class Player {
  constructor(player) {
    this.score = 0;
    this.right = 0;
    this.wrong = 0;
    this.lastRound = null;
    this.initCurrentRound(0);

    // override generated values with input fields
    // generated on Game Server: id, gameId, username, avatar, creationServer, gameServer
    if (player) {
      Object.assign(this, player);
    }

    this.scoringServer = CLUSTER_NAME;
  }

  initCurrentRound(index) {
    const item = itemData[index];
    this.currentRound = {
      itemId: item.id,
      choices: [...item.choices],
      answers: [...item.answers],
      image: item.image,
      points: 100,
      correct: false
    };
  }

  addGuess(guessAnswers) {
    this._updateAnswers(guessAnswers);
    this._scoreCurrentRound();
    this.scoringServer = CLUSTER_NAME;
  }

  _updateAnswers(guessAnswers) {
    let item = itemData[this.currentRound.itemId];
    this.currentRound.answers.forEach((currentAnswer, index) => {
      if (currentAnswer.format !== "number" || currentAnswer.result === "correct") {
        return; // skip, no need to answer
      }

      const guessAnswer = guessAnswers[index];
      if ((guessAnswer.result && guessAnswer.result !== "pending") || !Number.isInteger(guessAnswer.number)) {
        return; // skip, no attempt to answer
      }

      currentAnswer.number = guessAnswer.number;
      currentAnswer.from = guessAnswer.from;
      if (currentAnswer.number === item.price[index]) {
        currentAnswer.result = "correct";
        this.right += 1;
        this.currentRound.choices[guessAnswer.from] = null;
      } else {
        currentAnswer.result = "incorrect";
        this.wrong += 1;
        this.currentRound.points = this.currentRound.points - 5;
        if (currentAnswer.points < 0) {
          currentAnswer.points = 0;
        }
        this.currentRound.choices[guessAnswer.from] = item.choices[guessAnswer.from];
      }
    });
  }

  _scoreCurrentRound() {
    let currentRoundCorrect = true;
    for (let i = 0; i < this.currentRound.answers.length; i++) {
      let a = this.currentRound.answers[i];
      if (a.format === 'number' && a.result !== 'correct') {
        currentRoundCorrect = false;
      }
    }

    this.currentRound.correct = currentRoundCorrect;

    if (currentRoundCorrect) {
      this.lastRound = this.currentRound;
      this.score = this.score + this.lastRound.points;
      let nextItemId = this.lastRound.itemId + 1;
      if (nextItemId >= itemData.length) {
        nextItemId = 0;
      }
      this.initCurrentRound(nextItemId);
    }
  }
}

module.exports = Player;
