package org.happyuc.webuj.codegen;

import org.happyuc.webuj.TempFileProvider;

import org.junit.Test;


public class AbiTypesMapperGeneratorTest extends TempFileProvider {

    @Test
    public void testGeneration() throws Exception {
        AbiTypesMapperGenerator.main(new String[]{tempDirPath});
    }
}
