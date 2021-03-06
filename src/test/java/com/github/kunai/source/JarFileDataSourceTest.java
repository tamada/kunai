package com.github.kunai.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.kunai.Entry;
import com.github.kunai.source.DataSource;
import com.github.kunai.source.DataSourceBuilder;
import org.junit.Before;
import org.junit.Test;

public class JarFileDataSourceTest {
    private DataSource source;

    @Before
    public void setUp() throws Exception{
        source = DataSourceBuilder.getBuilder().build(
            "target/test-classes/resources/hello.jar"
        );
    }

    @Test
    public void testBasic() throws Exception{
        assertThat(source.getType(), is(DataSource.Type.JAR_FILE));
        assertThat(source.getBase(), is("target/test-classes/resources/hello.jar"));

        Iterator<Entry> iterator = source.iterator();
        assertTrue(iterator.hasNext());
        Entry entry1 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry2 = iterator.next();
        assertTrue(iterator.hasNext());
        Entry entry3 = iterator.next();
        assertThat(iterator.hasNext(), is(false));

        assertThat(entry1.getResourcePath(), is("META-INF/MANIFEST.MF"));
        assertThat(entry1.getType(), is(Entry.Type.RESOURCE));

        assertThat(entry2.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(entry2.getClassName(), is("hello.HelloWorld"));
        assertThat(entry2.getResourcePath(), is("hello/HelloWorld.class"));

        assertThat(entry3.getType(), is(Entry.Type.SOURCE_FILE));
        assertThat(entry3.getResourcePath(), is("hello/HelloWorld.java"));
    }

    @Test
    public void testStream() throws Exception{
        List<Entry> list = source.stream()
            .filter(f -> f.getResourcePath().endsWith(".class"))
            .collect(Collectors.toList());

        Entry entry = list.get(0);
        assertThat(list.size(), is(1));
        assertThat(entry.getType(), is(Entry.Type.CLASS_FILE));
        assertThat(entry.getClassName(), is("hello.HelloWorld"));
        assertThat(entry.getResourcePath(), is("hello/HelloWorld.class"));
    }
}
