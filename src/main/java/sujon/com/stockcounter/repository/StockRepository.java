package sujon.com.stockcounter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujon.com.stockcounter.repository.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {
}
