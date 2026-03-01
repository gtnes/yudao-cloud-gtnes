-- ========================================================
-- 修复软件使用记录表重复设备指纹问题
-- 添加唯一索引防止重复数据
-- ========================================================

-- 1. 首先清理重复数据，只保留每个设备指纹的最新记录
-- 注意：执行此操作前请备份数据！

-- 查看重复的设备指纹（调试用）
-- SELECT device_fingerprint, COUNT(*) as count
-- FROM infra_gtnes_software_record
-- WHERE deleted = 0
-- GROUP BY device_fingerprint
-- HAVING COUNT(*) > 1;

-- 2. 删除重复数据，只保留id最大的记录（即最新的记录）
DELETE t1 FROM infra_gtnes_software_record t1
INNER JOIN infra_gtnes_software_record t2
WHERE t1.id < t2.id
  AND t1.device_fingerprint = t2.device_fingerprint
  AND t1.deleted = 0
  AND t2.deleted = 0;

-- 3. 添加唯一索引，确保设备指纹唯一性（排除已删除的记录）
-- MySQL 语法
ALTER TABLE infra_gtnes_software_record
ADD UNIQUE INDEX uk_device_fingerprint (device_fingerprint);

-- 如果使用的是其他数据库，请使用对应的语法：

-- PostgreSQL 语法：
-- CREATE UNIQUE INDEX uk_device_fingerprint ON infra_gtnes_software_record (device_fingerprint)
-- WHERE deleted = false;

-- Oracle 语法：
-- CREATE UNIQUE INDEX uk_device_fingerprint ON infra_gtnes_software_record (device_fingerprint)
-- WHERE deleted = 0;

-- SQL Server 语法：
-- CREATE UNIQUE INDEX uk_device_fingerprint ON infra_gtnes_software_record (device_fingerprint)
-- WHERE deleted = 0;

-- 4. 验证索引是否创建成功
-- SHOW INDEX FROM infra_gtnes_software_record;
