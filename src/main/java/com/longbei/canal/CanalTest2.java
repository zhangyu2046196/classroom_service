package com.longbei.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description  canal同步biglog日志
 * @date 2020/3/14 22:24
 */
public class CanalTest2 {

    public static void main(String[] args) {
        System.out.println("canal同步binlog日志工具");

        new Thread(() -> initConnector("my1")).start();
        new Thread(() -> initConnector("my2")).start();

    }

    private static void initConnector(String destination) {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.1.21", 11111), destination, "", "");
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(1000);
                if (message.getId() != -1 && message.getEntries().size() > 0) {
                    printEntry(message.getEntries());
                }
                connector.ack(message.getId());
            }
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entries) {
        for (Entry entry : entries) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            try {
                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
                System.out.println(rowChange.getSql());

                CanalEntry.EventType eventType = rowChange.getEventType();
                System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                        entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType));
                System.out.println("表名:"+entry.getHeader().getTableName());
                System.out.println("操作类型:"+eventType);

                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    if (eventType == CanalEntry.EventType.DELETE) {
                        System.out.println("删除数据");
                        //redisDelete(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        System.out.println("插入数据");
                        //redisInsert(rowData.getAfterColumnsList());
//                        String line = columns.stream()
//                                .map(column -> column.getName() + "=" + column.getValue())
//                                .collect(Collectors.joining(","));
//                        System.out.println(line);
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        System.out.println("-------> before");
                        printColumn(rowData.getBeforeColumnsList());
                        System.out.println("-------> after");
                        //redisUpdate(rowData.getAfterColumnsList());
                        printColumn(rowData.getAfterColumnsList());
                    }
                }


            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser error, data:" + entry.toString(), e);
            }
        }
    }

    /**
     * 打印变化的数据
     *
     * @param columns
     */
    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println("列数据:"+column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

}
