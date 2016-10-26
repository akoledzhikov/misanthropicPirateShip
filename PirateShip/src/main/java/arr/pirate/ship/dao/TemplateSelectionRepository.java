package arr.pirate.ship.dao;


import java.util.Calendar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import arr.pirate.ship.model.TemplateSelection;


public interface TemplateSelectionRepository
    extends CrudRepository<TemplateSelection, Long>
{
    @Query("SELECT c FROM TemplateSelection c WHERE c.userId=:userId AND :now BETWEEN c.validFrom AND c.validTo")
    public TemplateSelection findValidSelection(@Param("userId") long userId,
                                                         @Param("now") Calendar now);
}
