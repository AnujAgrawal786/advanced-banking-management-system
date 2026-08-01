-- ============================================
-- ADVANCED BANKING MANAGEMENT SYSTEM
-- INITIAL MASTER DATA
-- ============================================

-- Insert system roles
INSERT INTO roles VALUES
(1, 'ADMIN', 'Full system administrator with complete access.');

INSERT INTO roles VALUES
(2, 'EMPLOYEE', 'Bank employee responsible for customer and account operations.');

INSERT INTO roles VALUES
(3, 'CUSTOMER', 'Bank customer with access to personal banking services.');


INSERT INTO customers
(customer_id, user_id, first_name, last_name,
 date_of_birth, address, city, state, pincode, phone)
VALUES
(2001, 1001, 'Anuj', 'Agrawal',
 DATE '2003-01-15', 'Address',
 'Panna', 'Madhya Pradesh',
 '488001', '9876543210');

COMMIT;