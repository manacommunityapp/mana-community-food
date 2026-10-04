-- Grocery Market, Smart Pantry, and Community Dining tables

CREATE TABLE IF NOT EXISTS food_grocery_items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(50) NOT NULL DEFAULT 'VEGETABLES',
    price NUMERIC(10, 2) NOT NULL,
    unit VARCHAR(20) NOT NULL DEFAULT 'kg',
    image_url VARCHAR(255),
    is_organic BOOLEAN DEFAULT FALSE,
    is_available BOOLEAN DEFAULT TRUE,
    stock_quantity INT DEFAULT 0,
    farmer_name VARCHAR(100),
    community_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_grocery_orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    community_id BIGINT NOT NULL,
    total_amount NUMERIC(10, 2) NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL DEFAULT 'PLACED',
    delivery_address VARCHAR(255),
    delivery_slot VARCHAR(50),
    payment_method VARCHAR(30),
    payment_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_grocery_order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES food_grocery_orders(id) ON DELETE CASCADE,
    grocery_item_id BIGINT NOT NULL,
    item_name VARCHAR(150) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price NUMERIC(10, 2) NOT NULL,
    unit VARCHAR(20) NOT NULL DEFAULT 'kg'
);

CREATE TABLE IF NOT EXISTS food_pantry_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    quantity NUMERIC(10, 2) NOT NULL DEFAULT 1,
    unit VARCHAR(20) NOT NULL DEFAULT 'pcs',
    category VARCHAR(50),
    expiry_date DATE,
    low_stock_threshold NUMERIC(10, 2) DEFAULT 1,
    image_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_pantry_user_expiry ON food_pantry_items(user_id, expiry_date);

CREATE TABLE IF NOT EXISTS food_dining_events (
    id BIGSERIAL PRIMARY KEY,
    community_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_type VARCHAR(50) NOT NULL DEFAULT 'COMMUNITY_DINNER',
    event_date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    organizer_id BIGINT NOT NULL,
    max_spots INT NOT NULL DEFAULT 50,
    spots_taken INT NOT NULL DEFAULT 0,
    cost_per_person NUMERIC(10, 2) DEFAULT 0,
    is_free BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    menu_description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'UPCOMING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_dining_reservations (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES food_dining_events(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL,
    guest_count INT NOT NULL DEFAULT 1,
    dietary_notes VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'CONFIRMED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_dining_event_user ON food_dining_reservations(event_id, user_id);
