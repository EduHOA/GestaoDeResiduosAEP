INSERT INTO waste (id, name, type, category, points_per_kg, instructions, created_at) VALUES
(1, 'Papel', 'PAPER', 'RECYCLABLE', 5, 'Remover grampos e fitas adesivas', NOW()),
(2, 'Plástico PET', 'PLASTIC', 'RECYCLABLE', 10, 'Limpar e remover rótulos', NOW()),
(3, 'Vidro', 'GLASS', 'RECYCLABLE', 15, 'Separar por cores, sem quebrar', NOW()),
(4, 'Metal', 'METAL', 'RECYCLABLE', 20, 'Limpar resíduos de alimentos', NOW()),
(5, 'Eletrônicos', 'ELECTRONIC', 'SPECIAL', 50, 'Remover baterias se possível', NOW());

-- Inserir pontos de coleta exemplo
INSERT INTO collection_point (id, name, address, latitude, longitude, phone, opening_hours, capacity, status, created_at) VALUES
(1, 'EcoPonto Centro', 'Rua das Flores, 123 - Centro', -23.5505, -46.6333, '(11) 1234-5678', '08:00-17:00', 1000, 'ACTIVE', NOW()),
(2, 'EcoPonto Jardins', 'Av. Paulista, 456 - Jardins', -23.5629, -46.6544, '(11) 8765-4321', '07:00-19:00', 1500, 'ACTIVE', NOW());

-- Inserir recompensas exemplo
INSERT INTO reward (id, name, description, points_required, type, available_quantity, created_at) VALUES
(1, 'Desconto 10% Supermercado', 'Desconto de 10% em compras acima de R$ 50', 100, 'DISCOUNT', 100, NOW()),
(2, 'Muda de Árvore', 'Muda de árvore nativa para plantio', 200, 'PRODUCT', 50, NOW()),
(3, 'Camiseta Ecológica', 'Camiseta feita de material reciclado', 300, 'PRODUCT', 25, NOW());