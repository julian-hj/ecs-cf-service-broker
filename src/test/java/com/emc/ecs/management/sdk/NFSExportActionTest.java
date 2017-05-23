package com.emc.ecs.management.sdk;

import com.emc.ecs.cloudfoundry.broker.EcsManagementClientException;
import com.emc.ecs.common.EcsActionTest;
import com.emc.ecs.management.sdk.model.NFSExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NFSExportActionTest extends EcsActionTest {
    private static final String EXPORT_PATH = "/ns1/ecs-cf-broker-bucket1/export-path";
    private static final int EXPORT_ID = 4;

    @Before
    public void setUp() throws EcsManagementClientException {
        connection.login();
    }

    @After
    public void cleanup() throws EcsManagementClientException {
        connection.logout();
    }

    @Test
    public void testCreateAndExistsExport() throws Exception {
        assertNull(NFSExportAction.list(connection, EXPORT_PATH));
        NFSExportAction.create(connection, EXPORT_PATH);
        List<NFSExport> exports = NFSExportAction.list(connection, EXPORT_PATH);
        assertEquals(1, exports.size());
        NFSExport export = exports.get(0);
        assertEquals(EXPORT_ID, export.getId().intValue());
        assertEquals(EXPORT_PATH, export.getPath());
        NFSExportAction.delete(connection, EXPORT_ID);
        assertNull(NFSExportAction.list(connection, EXPORT_PATH));
    }

}