const {CLUSTER_NAME} = require('../utils/constants');
const itemData = require('./item-data');

class Player {
  constructor(player) {
    this.score = {
      status: 'START',
        scoringServer: CLUSTER_NAME,
        score: 0,
        right: 1,
        wrong: 1,
        award: 0
    };

    this.lastRound = null;
    this.history = [];
    this.initCurrentRound(0);

    // override generated values with input fields
    // generated on Game Server: id, gameId, username, avatar, creationServer, gameServer
    if (player) {
      Object.assign(this, player);
    }
  }

  initCurrentRound(index) {
    const item = itemData[index];

    this.currentRound = {
      id: item.id,
      version: "1",
      pointsAvailable: 100,
      name: item.name,
      description: item.description,
      choices: [...item.choices],
      answers: [...item.answers],
      image: item.image,
      correct: false
    };

    this.history.push({
      itemId: item.id,
      itemName: item.name,
      right: 0,
      wrong: 0,
      pointsAvailable: null
    });
  }

  addGuess(guessAnswers) {
    this._updateAnswers(guessAnswers);
    this._scoreCurrentRound();
    this.scoringServer = CLUSTER_NAME;
  }

  _updateAnswers(guessAnswers) {
    let item = itemData[this.currentRound.id];
    let historyRecord = this.history[this.history.length - 1];
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
        this.score.right += 1;
        historyRecord.right += 1;
        if (guessAnswer.from !== 'bonus') {
          this.currentRound.choices[guessAnswer.from] = null;
        }
      } else {
        currentAnswer.result = "incorrect";
        this.score.wrong += 1;
        historyRecord.wrong += 1;
        this.currentRound.pointsAvailable = this.currentRound.pointsAvailable - 5;
        if (this.currentRound.pointsAvailable < 0) {
          this.currentRound.pointsAvailable = 0;
        }
        if (guessAnswer.from !== 'bonus') {
          this.currentRound.choices[guessAnswer.from] = item.choices[guessAnswer.from];
        }
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
      this.score.score = this.score.score + this.lastRound.pointsAvailable;
      let nextItemId = this.lastRound.id + 1;
      if (nextItemId >= itemData.length) {
        nextItemId = 0;
      }
      let historyRecord = this.history[this.history.length - 1];
      historyRecord.points = this.lastRound.pointsAvailable;
      this.initCurrentRound(nextItemId);
    }
  }
}

module.exports = Player;
