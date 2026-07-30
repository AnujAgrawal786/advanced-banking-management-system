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

COMMIT;