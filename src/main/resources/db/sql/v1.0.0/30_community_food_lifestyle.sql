-- CFLOS Community Food & Lifestyle Operating System Initial Schema

CREATE TABLE IF NOT EXISTS food_restaurants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    cuisine_type VARCHAR(50),
    image_url VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(120),
    address VARCHAR(255),
    rating NUMERIC(3, 2) DEFAULT 4.50,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    status VARCHAR(30) NOT NULL DEFAULT 'APPROVED',
    community_id BIGINT,
    owner_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_menu_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    display_order INT DEFAULT 0,
    restaurant_id BIGINT NOT NULL REFERENCES food_restaurants(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS food_menu_items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(10, 2) NOT NULL,
    image_url VARCHAR(255),
    is_veg BOOLEAN DEFAULT TRUE,
    is_available BOOLEAN DEFAULT TRUE,
    is_combo BOOLEAN DEFAULT FALSE,
    category_id BIGINT REFERENCES food_menu_categories(id) ON DELETE SET NULL,
    restaurant_id BIGINT NOT NULL REFERENCES food_restaurants(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS food_orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    total_amount NUMERIC(10, 2) NOT NULL,
    status VARCHAR(40) NOT NULL DEFAULT 'PLACED',
    delivery_address VARCHAR(255),
    payment_method VARCHAR(30),
    payment_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES food_orders(id) ON DELETE CASCADE,
    menu_item_id BIGINT NOT NULL,
    item_name VARCHAR(150) NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS food_subscription_plans (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(10, 2) NOT NULL,
    plan_type VARCHAR(30),
    restaurant_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_recipes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    ingredients TEXT,
    instructions TEXT,
    prep_time_minutes INT,
    cook_time_minutes INT,
    image_url VARCHAR(255),
    author_id BIGINT NOT NULL,
    community_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS food_resident_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    dietary_preference VARCHAR(50),
    allergies VARCHAR(255),
    health_goals VARCHAR(255),
    ai_lifestyle_score INT DEFAULT 85,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
