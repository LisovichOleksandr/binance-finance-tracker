CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    binance_api_key VARCHAR(255),
    binance_secret_key VARCHAR(255),
);
ALTER TABLE users ADD COLUMN created_at TIMESTAMP not null default current_timestamp;
ALTER TABLE users ADD COLUMN updated_at TIMESTAMP;


CREATE TABLE symbols (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(20) UNIQUE NOT NULL,
    base_asset VARCHAR(20) NOT NULL,
    quote_asset VARCHAR(20) NOT NULL
);

CREATE TABLE prices (
    id BIGSERIAL PRIMARY KEY,
    symbol_id BIGINT NOT NULL REFERENCES symbols(id),
    price NUMERIC(18,8) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    symbol_id BIGINT NOT NULL REFERENCES symbols(id),
    side VARCHAR(4) NOT NULL,        -- BUY / SELL
    quantity NUMERIC(20,8) NOT NULL,
    price NUMERIC(20,8) NOT NULL,
    status VARCHAR(20) NOT NULL,     -- NEW / FILLED / CANCELED
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

