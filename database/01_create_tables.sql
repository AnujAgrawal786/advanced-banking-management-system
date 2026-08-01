-- ============================================
-- ADVANCED BANKING MANAGEMENT SYSTEM
-- DATABASE TABLE CREATION SCRIPT
-- ============================================

-- 1. ROLES
CREATE TABLE roles (
    role_id NUMBER NOT NULL,
    role_name VARCHAR2(30) NOT NULL,
    role_description VARCHAR2(200),
    CONSTRAINT pk_roles PRIMARY KEY (role_id),
    CONSTRAINT uq_roles_name UNIQUE (role_name)
);

-- 2. USERS
CREATE TABLE users (
    user_id NUMBER NOT NULL,
    username VARCHAR2(50) NOT NULL,
    password_hash VARCHAR2(255) NOT NULL,
    email VARCHAR2(100) NOT NULL,
    mobile VARCHAR2(15) NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_mobile UNIQUE (mobile)
);

-- 3. USER_ROLES
CREATE TABLE user_roles (
    user_id NUMBER NOT NULL,
    role_id NUMBER NOT NULL,
    role_description VARCHAR2(200),
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id)
        REFERENCES roles(role_id)
);

-- 4. CUSTOMERS
CREATE TABLE customers (
    customer_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR2(200),
    city VARCHAR2(50),
    state VARCHAR2(50),
    pincode VARCHAR2(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (customer_id),
    CONSTRAINT uq_customers_user UNIQUE (user_id),
    CONSTRAINT fk_customers_user FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

-- 5. ACCOUNTS
CREATE TABLE accounts (
    account_id NUMBER NOT NULL,
    customer_id NUMBER NOT NULL,
    account_number VARCHAR2(20) NOT NULL,
    account_type VARCHAR2(20) NOT NULL,
    balance NUMBER(15,2) DEFAULT 0 NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    opened_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (account_id),
    CONSTRAINT uq_accounts_number UNIQUE (account_number),
    CONSTRAINT ck_accounts_type CHECK (account_type IN ('SAVINGS', 'CURRENT')),
    CONSTRAINT ck_accounts_balance CHECK (balance >= 0),
    CONSTRAINT ck_accounts_status CHECK (status IN ('ACTIVE', 'FROZEN', 'CLOSED')),
    CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);

-- 6. TRANSACTIONS
CREATE TABLE transactions (
    transaction_id NUMBER NOT NULL,
    transaction_reference VARCHAR2(50) NOT NULL,
    transaction_type VARCHAR2(20) NOT NULL,
    amount NUMBER(15,2) NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL,
    initiated_by NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (transaction_id),
    CONSTRAINT uq_transactions_reference UNIQUE (transaction_reference),
    CONSTRAINT ck_transactions_type CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
    CONSTRAINT ck_transactions_amount CHECK (amount > 0),
    CONSTRAINT ck_transactions_status CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED', 'REVERSED')),
    CONSTRAINT fk_transactions_user FOREIGN KEY (initiated_by)
        REFERENCES users(user_id)
);

-- 7. TRANSACTION_ENTRIES
CREATE TABLE transaction_entries (
    entry_id NUMBER NOT NULL,
    transaction_id NUMBER NOT NULL,
    account_id NUMBER NOT NULL,
    entry_type VARCHAR2(10) NOT NULL,
    amount NUMBER(15,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_transaction_entries PRIMARY KEY (entry_id),
    CONSTRAINT ck_transaction_entries_type CHECK (entry_type IN ('DEBIT', 'CREDIT')),
    CONSTRAINT ck_transaction_entries_amount CHECK (amount > 0),
    CONSTRAINT fk_entries_transaction FOREIGN KEY (transaction_id)
        REFERENCES transactions(transaction_id),
    CONSTRAINT fk_entries_account FOREIGN KEY (account_id)
        REFERENCES accounts(account_id)
);

-- 8. BENEFICIARIES
CREATE TABLE beneficiaries (
    beneficiary_id NUMBER NOT NULL,
    customer_id NUMBER NOT NULL,
    beneficiary_name VARCHAR2(100) NOT NULL,
    account_number VARCHAR2(20) NOT NULL,
    bank_name VARCHAR2(100) NOT NULL,
    ifsc_code VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_beneficiaries PRIMARY KEY (beneficiary_id),
    CONSTRAINT ck_beneficiaries_status CHECK (status IN ('PENDING', 'ACTIVE', 'BLOCKED')),
    CONSTRAINT fk_beneficiaries_customer FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);

-- 9. AUDIT_LOGS
CREATE TABLE audit_logs (
    log_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    action VARCHAR2(50) NOT NULL,
    description VARCHAR2(500),
    ip_address VARCHAR2(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pk_audit_logs PRIMARY KEY (log_id),
    CONSTRAINT fk_audit_logs_user FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);