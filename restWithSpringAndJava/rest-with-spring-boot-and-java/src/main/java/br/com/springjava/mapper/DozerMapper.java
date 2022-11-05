package br.com.springjava.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

// Classe do Dozer que vai mapear ObjectVO para entidade e vice versa
public class DozerMapper {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	//Método que mapeia 1 ObjectVO para entidade
	public static <O, D> D parseObject(O origin, Class<D> destination) {

		return mapper.map(origin, destination);
		
	}

	//Método que mapeia uma lista de ObjectVO para entidade
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

		List<D> destinationObjects = new ArrayList<D>();
		
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));

		}

		return destinationObjects;
	}

}
