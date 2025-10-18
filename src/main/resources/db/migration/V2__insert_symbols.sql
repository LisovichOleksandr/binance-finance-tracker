INSERT INTO roles (role)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- 1. Биткоин к Tether (USDT) – самая ликвидная пара
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('BTCUSDT', 'BTC', 'USDT');

-- 2. Эфириум к USDT – вторая по популярности пара
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('ETHUSDT', 'ETH', 'USDT');

-- 3. Binance Coin (BNB) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('BNBUSDT', 'BNB', 'USDT');

-- XRP к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('XRPUSDT', 'XRP', 'USDT');

-- 5. Cardano (ADA) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('ADAUSDT', 'ADA', 'USDT');

-- 6. Dogecoin (DOGE) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('DOGEUSDT', 'DOGE', 'USDT');

-- 7. Solana (SOL) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('SOLUSDT', 'SOL', 'USDT');

-- 8. Polygon (MATIC) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('MATICUSDT', 'MATIC', 'USDT');

-- 9. Polkadot (DOT) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('DOTUSDT', 'DOT', 'USDT');

 -- 10. Litecoin (LTC) к USDT
INSERT INTO symbols (symbol, base_asset, quote_asset)
VALUES ('LTCUSDT', 'LTC', 'USDT');
