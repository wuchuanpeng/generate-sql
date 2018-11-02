package cc.nonone.generatesql.web;

import cc.nonone.generatesql.common.utils.CommonUtil;
import cc.nonone.generatesql.common.utils.ReadExcelUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * controller
 * @author NonOne
 * @date 2018/11/1 10:21
 */
@Controller
@RequestMapping
public class GenSqlByExcelController {
    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping("")
    public String genWeb(){
        return "gen_sql";
    }

    /**
     * 生成SQL
     * @param dictFile 上传的数据库设计文档
     * @param tableSplit 数据表表分割字符串
     * @return
     */
    @PostMapping("genSQL")
    public ResponseEntity<byte[]> genSQL(@RequestParam("dictFile") MultipartFile dictFile, String tableSplit){
        long startTime =System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String originalFilename = dictFile.getOriginalFilename();
        //获取文件名
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + ".sql";
        try {
            //读取EXCEL数据
            List<String[]> list = ReadExcelUtils.getRowCellValues(dictFile.getInputStream());
            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("gb2312"),
                    "iso-8859-1"));
            long endTime =System.currentTimeMillis();
            long times=(endTime-startTime);
            System.out.println("耗时======"+times+"ms");
            return new ResponseEntity<byte[]>(getSqlByte(list,tableSplit),headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对EXCEL的数据进行拼接
     * @param list 数据
     * @param tableSplit 数据表分割字符串
     * @return
     */
    private byte[] getSqlByte(List<String[]> list,String tableSplit) {
        if (CommonUtil.isBlank(tableSplit)) {
            tableSplit = "TABLE_END_END_END";
        }
        //从第三行开始
        if (CommonUtil.isNotNullList(list) && list.size() > 3){
            StringBuffer sb = new StringBuffer();
            String tableComment = "";
            String pk = "";
            boolean isOne = false;
            for (int  i = 2; i < list.size(); i++) {
                String[] row = list.get(i);
                //如果行为空或者第一个单元格为空则认为是无效数据
                if (CommonUtil.isNull(row) && CommonUtil.isBlank(row[0])) {
                    continue;
                }
                if (tableSplit.equals(row[0])) {
                    //如果第一个单元格是分割符,认为是表的结束
                    sb.append("\tPRIMARY KEY (`" + pk + "`) USING BTREE\n" +
                            " ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='" + tableComment + "';\n\n");
                    continue;
                }
                if (CommonUtil.isBlank(row[1])) {
                    //如果第二个单元格是空,认为是表开始
                    sb.append("CREATE TABLE `" + row[0] + "` ( \n");
                    tableComment = "";
                    if (CommonUtil.isNotBlank(row[4])){
                        tableComment = row[4].replace("\"","“");
                        tableComment = tableComment.replace("'","’");
                    }
                    isOne = true;
                    continue;
                }

                sb.append("\t`" + row[0] + "` " + row[1] + " ");
                if (CommonUtil.isNotBlank(row[2]) && "否".equals(row[2])) {
                    sb.append(" NOT NULL ");
                }
                if (CommonUtil.isNotBlank(row[3])) {
                    sb.append(" DEFAULT '" + row[3] + "' " );
                }

                if (CommonUtil.isNotBlank(row[4])) {
                    //替换注释的特殊字符
                    String comment = row[4].replace("\"","“");
                    comment = comment.replace("'","’");
                    sb.append(" COMMENT '" + comment + "' ");
                }
                sb.append(",\n");
                if (isOne) {
                    //如果是一个数据表的第一个字段,认为该字段是主键
                    pk = row[0];
                    isOne = false;
                }

            }
            try {
                return sb.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("转码错误" + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
