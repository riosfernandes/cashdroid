select
	g.data as data,
	sum(g.valor) as total
from
	gasto as g	
where
	g.subcategoriaId = 0 and	
	g.usuarioId = 2 and
	g.categoriaId = 10 and
	g.data between '2010-12-31' and '2011-02-11'
group by
	g.data
order by
	g.data
	