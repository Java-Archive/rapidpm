package org.rapidpm.orm.prj.stammdaten.kommunikation;

import org.rapidpm.orm.EntityFactory;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 14:05
 */
public class KommunikationsServiceFactory extends EntityFactory<KommunikationsService> {
    public KommunikationsServiceFactory() {
        super(KommunikationsService.class);
    }

    @Override
    public KommunikationsService createRandomEntity() {
        final KommunikationsService kommunikationsService = new KommunikationsService();
        kommunikationsService.setServiceName(RND.nextWord(5, 18));
        return kommunikationsService;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println(createRandomEntity());
    }
}
