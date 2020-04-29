# 2020-demo-scoring

### API
Complete API documentation can be found [here](API.md)

### Build
```.bash
make build
```

### Run dev mode
Loads env variables from `.env.default` and overrides in `.env` and runs `mvn quarkus:dev`.  Modify your kafka connection information and cluster name as needed in the `.env` file.
```.bash
make dev
```