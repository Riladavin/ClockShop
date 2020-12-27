-- migrations up: only by hands
CREATE TABLE IF NOT EXISTS clocks(
    id              SERIAL              NOT NULL,
    name            VARCHAR             NOT NULL,

    PRIMARY KEY(id)
);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS search_by_id ON clocks(id);
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS unique_names ON clocks(name);

CREATE TABLE IF NOT EXISTS clock_shops(
    clock_shop_id       SERIAL              NOT NULL,
    name                VARCHAR             NOT NULL,

    PRIMARY KEY (clock_shop_id)
);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS shops_search_by_id ON clocks(id);
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS shops_unique_names ON clocks(name);

CREATE TABLE IF NOT EXISTS clocks_for_shops(
    clock_shop_id           INT              NOT NULL,
    clock_id                INT              NOT NULL,

    PRIMARY KEY (clock_shop_id, clock_id)
);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS clocks_for_shops_search ON clocks_for_shops(clock_shop_id, clock_id);

-- migrations down: only by hands
DROP INDEX IF EXISTS clocks_for_shops_search;
DROP INDEX IF EXISTS search_by_id;
DROP INDEX IF EXISTS unique_names;
DROP INDEX IF EXISTS shops_search_by_id;
DROP INDEX IF EXISTS shops_unique_names;
DROP TABLE IF EXISTS clock_shops;
DROP TABLE IF EXISTS clocks;
DROP TABLE IF EXISTS clocks_for_shops;