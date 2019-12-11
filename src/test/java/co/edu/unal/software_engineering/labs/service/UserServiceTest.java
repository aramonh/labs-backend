package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.resu;
import co.edu.unal.software_engineering.labs.pojo.RegisterresuPOJO;
import co.edu.unal.software_engineering.labs.repository.resuRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith( SpringRunner.class )
@DataJpaTest
@AutoConfigureTestDatabase( replace = NONE )
public class resuServiceTest{

    @TestConfiguration
    static class resuServiceImpTestConfiguration{
        @Autowired
        private resuRepository resuRepository;

        @Bean
        public resuService resuService( ){
            return new resuService( resuRepository );
        }
    }

    @Autowired
    private resuService resuService;

    @Test
    public void crudTest( ){
        String names = "Test resu";
        String surnames = "Labs SE-II UN";
        String resuname = "test";

        resu createresu = new resu( );
        createresu.setNames( names );
        createresu.setSurnames( surnames );
        createresu.setresuname( resuname );
        createresu.setPassword( resuname );
        resuService.save( createresu );

        resu readresu = resuService.findByresuname( resuname );
        assertEquals( createresu, readresu );

        createresu.addRole( Role.getStudent( ) );
        resuService.save( createresu );

        resu updatedresu = resuService.findByresuname( resuname );
        assertEquals( createresu.getRoles( ), updatedresu.getRoles( ) );
    }

    @Test
    public void isRightresuTest( ){
        RegisterresuPOJO resu = new RegisterresuPOJO( );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( null );
        resu.setPassword( "" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setPassword( null );
        resu.setSurnames( "" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setSurnames( null );
        resu.setresuname( "" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "" );
        resu.setSurnames( "" );
        resu.setPassword( "" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "   " );
        resu.setSurnames( "   " );
        resu.setPassword( "   " );
        resu.setresuname( "   " );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "test" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "   " );
        resu.setSurnames( "Test" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setSurnames( "   " );
        resu.setPassword( "Test" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setPassword( "   " );
        resu.setresuname( "Test" );
        assertFalse( resuService.isRightresu( resu ) );

        resu.setNames( "Test" );
        resu.setSurnames( "Test" );
        resu.setPassword( "Test" );
        resu.setresuname( "Test" );
        assertTrue( resuService.isRightresu( resu ) );
    }

}
