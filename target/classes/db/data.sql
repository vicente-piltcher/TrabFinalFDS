INSERT INTO produto (id,descricao,preco_unitario) VALUES 
        (10,'Televisor',2000.0),
        (20,'Geladeira',3500.0),
        (30,'Fogao',1200.0),
        (40,'Lava-louça',1800.0),
        (50,'lava-roupas',2870.0);

INSERT INTO item_de_estoque (id, produto_id, quantidade, estoque_min, estoque_max ) VALUES 
        (100,10,20,5,50),
        (200,20,10,5,30),
        (300,40,8,5,50);

        