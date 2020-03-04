# JSON spec

## Join Game
### POST /game/join
#### Request Body: Player information
```json
{
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
}
```

#### Response Body

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
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

#### Request Body:

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
    },
    "score": 0,
    "right": 0,
    "wrong": 0,
    "currentRound": {
        "id": 0,
        "pointsAvailable": 100,
        "guess": [1, "."]
    }
}
```

#### Response Body

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "correctGuess": true,
    "score": 0,
    "award": 0,
    "right": 1,
    "wrong": 0,
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
        "pointsAvailable": 100,
        "name": "Dollar bill",
        "description": "One United States dollar and no cents",
        "guess": [1, "."],
        "choices": [9, 0, 5, 0, 1],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/0.jpg"
    }
}
```

### Bad 2nd Guess POST /game/score

#### Request Body:

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
    },
    "score": 0,
    "right": 1,
    "wrong": 0,
    "currentRound": {
        "id": 0,
        "pointsAvailable": 100,
        "guess": [1, ".", 5]
    }
}
```

#### Response Body

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "correctGuess": false,
    "score": 0,
    "award": 0,
    "right": 1,
    "wrong": 1,
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
        "pointsAvailable": 95,
        "name": "Dollar bill",
        "description": "One United States dollar and no cents",
        "guess": [1, "."],
        "choices": [9, 0, 5, 0, 1],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/0.jpg"
    }
}
```

### Final All Correct Guess POST /game/score

#### Request Body:

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "player": {
        "id": "Emerald Wanderer",
        "username": "Emerald Wanderer",
    },
    "score": 0,
    "right": 2,
    "totalCorrect": 1,
    "currentRound": {
        "id": 0,
        "pointsAvailable": 95,
        "guess": [1, ".", 0, 0]
    }
}
```

#### Response Body

```json
{
    "creationServer": "SFO",
    "gameServer": "SFO",
    "scoringServer": "NY",
    "correctGuess": true,
    "score": 95,
    "award": 95,
    "right": 3,
    "wrong": 1,
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
        "pointsAvailable": 100,
        "name": "Kernel of truth t-shirt",
        "description": "This 4.3 ounce, 60% combed ringspun...",
        "guess": [],
        "choices": [5, 4, 8, 5, 7, 8],
        "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
        "image": "/static/images/1.jpg"
    }
}
```

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