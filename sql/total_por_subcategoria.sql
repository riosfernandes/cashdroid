select
	gasto.subcategoriaId,
	case when subcategoria.descricao is null 
	then
		'Outros'
	else
		subcategoria.descricao
	end,
	sum(gasto.valor)
from
	gasto left join subcategoria on 
		gasto.subcategoriaId = subcategoria.Id and gasto.usuarioId = 2 and gasto.categoriaId = 3 and gasto.data between '2010-06-31' and '2011-02-30'
group by
	subcategoria.Id
order by
	subcategoria.descricao