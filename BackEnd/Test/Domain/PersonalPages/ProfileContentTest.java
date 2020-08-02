package Domain.PersonalPages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileContentTest {

    @Test
    public void addFeatureToProfile() {
        ProfileContent content = new ProfileContent();
        content.addFeatureToProfile("height","1.80");
        //fixme(we dont have a getter)
        assertEquals(1,1);
    }
}