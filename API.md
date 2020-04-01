# JSON spec

## Join Game
### POST /game/join
#### Request Body: Player information
```json
{
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "creationServer": "SFO",
        "gameServer": "SFO",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
}
```

#### Join Response Body

```json
{
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "creationServer": "SFO",
        "gameServer": "SFO",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "score": {
        "status": "START",
        "scoringServer": "NYC",
        "score": 0,
        "right": 0,
        "wrong": 0,
        "award": 0
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 100,
        "name": "Dollar bill",
        "description": "One United States dollar and no cents",
        "guess": [],
        "choices": [9, 1, 0, 5, 0, 1],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/0.jpg"
    }
}
```

### Correct 1st Guess POST /game/score

#### Guess Request Body:

```json
{
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "score": {
        "score": 0,
        "right": 0,
        "wrong": 0,
        "award": 0
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 100,
        "guess": [1, ".", "", ""],
        "choices": [9, 'guess', 0, 5, 0, 1],
    }
}
```

#### Notes on request body

`currentRound.guess` contains previous correct guesses and current guess.  If the digit is not guessed
yet, then an empty string should be in its place.  The guess must contain the decimal place, "." as well.

`currentRound.choices` is an array of current digits that are available to guess.  The current guess should
replace the array entry with "guess".

#### Good Guess Response Body

```json
{
    "score": {
        "status": "CORRECT_GUESS",
        "scoringServer": "NYC",
        "score": 0,
        "right": 1,
        "wrong": 0,
        "award": 0
    },
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 100,
        "name": "Dollar bill",
        "description": "One United States dollar and no cents",
        "guess": [1, ".", "", ""],
        "guessResult": {
            "submittedGuess": [1, ".", "", ""],
            "answer": ["CORRECT", "CORRECT", "PENDING", "PENDING"]
        },
        "choices": [9, 'correct', 0, 5, 0, 1],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/0.jpg"
    }
}
```

#### Notes on bad guess response body

`score.right` is incremented on a correct digit guess.

`currentGuess.guess` will repeat the input guess if correct.  `currentRound.choices` choice that was guessed
will be replaced by "correct".


### Bad 2nd Guess POST /game/score

#### Bad Guess Request Body:

```json
{
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "score": {
        "score": 0,
        "right": 1,
        "wrong": 0,
        "award": 0
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 100,
        "guess": [1, ".", 5, ""],
        "choices": [9, "correct", 0, "guess", 0, 1],
    }
}
```

#### Bad guess request body notes

Notice that the `score` returned in previous response is resent.  The scoring service does not hold
any player state.  This must be passed by the UI.

Notice that `currentRound.choices` has a "correct" entry for the previous choice guesssed right.

#### Bad Guess Response Body

```json
{
    "score": {
        "status": "BAD_GUESS",
        "scoringServer": "NYC",
        "score": 0,
        "right": 1,
        "wrong": 1,
        "award": 0
    },
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 95,
        "name": "Dollar bill",
        "description": "One United States dollar and no cents",
        "guess": [1, ".", "", ""],
        "guessResult": {
            "submittedGuess": [1, ".", 9, ""],
            "answer": ["CORRECT", "CORRECT", "WRONG", "PENDING"]
        },
        "choices": [9, 'correct', 0, 5, 0, 1],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/0.jpg"
    }
}
```

#### Notes on bad guess response

`score.wrong` is incremented on a bad guess.

`currentRound.guess` is reset to only correct guesses.  `currentRound.choices` removes the incorrect "guess" entry.

### Final All Correct Guess POST /game/score

Skpping ahead after making another correct guess.

#### Final Guess Request Body:

```json
{
    "score": {
        "score": 0,
        "right": 2,
        "wrong": 1,
        "award": 0
    },
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "currentRound": {
        "id": 0,
        "version": "1",
        "pointsAvailable": 95,
        "guess": [1, ".", 0, 0],
        "choices": [9, "correct", "correct", 5, "guess", 1],
    }
}
```

#### Final Guess Response Body

```json
{
    "score": {
        "status": "COMPLETED_ROUND",
        "scoringServer": "NYC",
        "score": 95,
        "right": 3,
        "wrong": 1,
        "award": 95
    },
    "game": {
        "id": "new-game-1583157438",
        "state": "active",
        "date": "2020-03-02T13:57:18.000Z",
        "configuration": {}
    },
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
        "avatar": {
            "body": 1,
            "eyes": 3,
            "mouth": 0,
            "ears": 2,
            "nose": 1,
            "color": 3
        }
    },
    "currentRound": {
        "id": 1,
        "version": "1",
        "pointsAvailable": 100,
        "name": "Kernel of truth t-shirt",
        "description": "This 4.3 ounce, 60% combed ringspun...",
        "guess": [],
        "guessResult": {
            "submittedGuess": [1, ".", 0, 0],
            "answer": ["CORRECT", "CORRECT", "CORRECT", "CORRECT"]
        },
        "choices": [5, 4, 8, 5, 7, 8],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/1.jpg"
    }
}
```

#### Notes on Final All Correct Guess response

`score.award` is set to the award given.  `score.right` is incremeted.  `score.score` is incremented.


### Scoring Kafka Message
Scoring Server -> Kafka mirrored to HQ
```json
{
  "player": {
    "gameId": "new-game-1583157438",
    "id": "Emerald Wanderer",
    "username": "Emerald Wanderer",
    "score": 95,
    "right": 3,
    "wrong": 1,
    "avatar": {
      "body": 1,
      "eyes": 3,
      "mouth": 0,
      "ears": 2,
      "nose": 1,
      "color": 3
    },
    "creationServer": "SFO",
    "gameServer": "NY",
    "scoringServer": "NY"
  }
}
```