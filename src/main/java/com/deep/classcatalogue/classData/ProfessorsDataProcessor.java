package com.deep.classcatalogue.classData;

import com.deep.classcatalogue.classModel.Professors;

import org.springframework.batch.item.ItemProcessor;

/**
 * INTERMIDATE PROCESSOR
 * 
 * A common paradigm in batch processing is to ingest data, transform it, and then
 * pipe it out somewhere else. 
 */
public class ProfessorsDataProcessor implements ItemProcessor<ProfessorsData, Professors>{

    @Override
    public Professors process(ProfessorsData input) throws Exception {
      
        // ingest the input and set the data in the model
        Professors profs = new Professors();
        profs.setId(input.getId());
        profs.setName(input.getName());
        profs.setSubject(input.getSubject());
        profs.setLocation(input.getLocation());
        profs.setAvailability(input.getAvailability());
        return profs;
    }
    
}
