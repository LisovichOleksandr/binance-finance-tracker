
CREATE TABLE t_interval (
    id BIGSERIAL PRIMARY KEY,
    period VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO t_interval (period) VALUES
('1s'),
('1m'),
('3m'),
('5m'),
('15m'),
('30m'),
('1h'),
('2h'),
('4h'),
('6h'),
('8h'),
('12h'),
('1d'),
('3d'),
('1w'),
('1M');

CREATE TABLE history (
    id BIGSERIAL PRIMARY KEY,
    symbol_id BIGINT NOT NULL REFERENCES symbols(id),
    interval BIGINT NOT NULL REFERENCES t_interval(id),
    open_time TIMESTAMP,
    open_price DECIMAL(18,8),
    high_price DECIMAL(18,8),
    low_price DECIMAL(18,8),
    close_price DECIMAL(18,8),
    volume DECIMAL(18,8),
    close_time TIMESTAMP
);
