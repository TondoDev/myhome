package org.tondo.myhome.data.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;
import org.tondo.myhome.data.domain.ShareSummary;

/**
 * 
 * @author Anton Zsíros
 *
 */
public interface FondPaymentRepository extends CrudRepository<FondPayment, Long> {

	List<FondPayment> findByParentFondOrderByDateOfPurchaseDescIdDesc(Fond fond); 
	
//	@Query("select f.parentFond.id as fondId, sum(f.fee) as totalFees, sum(f.buyPrice/f.unitPrice) as ownedUnitsCount, sum(f.fee + f.buyPrice) as totalBuyPrice from #{#entityName} f where parentFond = ?1 group by f.parentFond.id")
//	ShareSummary getSumOfPaymentsAndFees(Fond fond);
	
	@Query("select f.parentFond.id as fondId, sum(f.fee) as totalFees, sum(purchasedUnits) as ownedUnitsCount, sum(f.buyPrice) as totalBuyPrice "
			+ "from #{#entityName} f where parentFond = ?1 and (?2 is null or dateOfPurchase <= ?2) "
			+ "group by parentFond")
	ShareSummary getSumOfPaymentsAndFees(Fond fond, LocalDate forDate);
	
	@Query("select f.parentFond.id as fondId, sum(f.fee) as totalFees, sum(f.purchasedUnits) as ownedUnitsCount, sum(f.fee + f.buyPrice) as totalBuyPrice from #{#entityName} f  group by parentFond")
	List<ShareSummary> getSumOfPaymentsAndFees();
	
	FondPayment findTopByParentFondOrderByDateOfPurchaseDescIdDesc(Fond parentFond);
	
	FondPayment findTopByParentFondAndDateOfPurchaseLessThanEqualOrderByDateOfPurchaseDescIdDesc(Fond parentFond, LocalDate maxDate);
	
	FondPayment findByParentFondIdAndId(Long parentFondId, Long id);
}

