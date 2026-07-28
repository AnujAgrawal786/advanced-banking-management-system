-- ============================================
-- ADVANCED BANKING MANAGEMENT SYSTEM
-- INITIAL MASTER DATA
-- ============================================

-- Insert system roles
INSERT INTO roles (role_id, role_name)
VALUES (seq_role_id.NEXTVAL, 'ADMIN');

INSERT INTO roles (role_id, role_name)
VALUES (seq_role_id.NEXTVAL, 'CUSTOMER');

INSERT INTO roles (role_id, role_name)
VALUES (seq_role_id.NEXTVAL, 'EMPLOYEE');

COMMIT;