select 
	categoria.usuarioId, 
	categoria.id, 
	categoria.descricao, 
	sum(gasto.valor) 
from 
	categoria, 
	gasto 
where 
	categoria.usuarioId = 1 and
	gasto.categoriaId = categoria.id and 
	gasto.usuarioId = categoria.usuarioId and 	
	gasto.data between '2010-12-31' and '2011-02-09' 
group by 
	categoria.id;