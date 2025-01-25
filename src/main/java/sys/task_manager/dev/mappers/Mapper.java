package sys.task_manager.dev.mappers;

import java.util.List;

public interface Mapper<E,D> {
    D toDto(E entity);

    List<D> toDto(List<E> entity);

    E toEntity(D dto);
}
