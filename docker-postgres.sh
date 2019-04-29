#!/bin/bash

docker run --detach \
  --name postgres-db \
  --env POSTGRES_PASSWORD=ferienpass-admin \
  --env POSTGRES_USER=ferienpass-admin \
  --publish 5432:5432 \
  postgres