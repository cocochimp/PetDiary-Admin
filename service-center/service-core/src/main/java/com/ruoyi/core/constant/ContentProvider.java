package com.ruoyi.core.constant;

import com.ruoyi.core.enums.ContentType;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;
import java.util.Optional;

public class ContentProvider {
//    private static Logger logger= LoggerFactory.getLogger(ContentProvider.class);

    public String showAllContent(Map<String,Object> para){
        //条件语句conditional
        String conditional = Optional.ofNullable(para.get("conditional"))
                .map(Object::toString).orElse(null);
        String sql = new SQL() {{
            SELECT("content_id,title,user_id,content_type,cover_path,update_time");
            FROM("user_content");
            //动态SQL
            if (para.get("contentType") != null) {
                String contentType = String.valueOf(para.get("contentType"));
                switch (Integer.parseInt(contentType)) {
                    case ContentTypeConstant.petCategoryContent:
                        WHERE("pet_id=" + conditional); break;
                    case ContentTypeConstant.followContent:
                        WHERE("user_id in ( " + conditional + " ) "); break;
                    case ContentTypeConstant.picContent:
                        WHERE("content_type=" + ContentType.PICTURE.getCode()); break;
                    case ContentTypeConstant.videoContent:
                        WHERE("content_type=" + ContentType.VIDEO.getCode()); break;
                    case ContentTypeConstant.catContent:
                    case ContentTypeConstant.dogContent:
                        WHERE("pet_id in ( " + conditional + " ) "); break;
                    case ContentTypeConstant.userContent:
                        assert conditional != null;
                        String[] split = conditional.split(",");
                        WHERE("user_id=" + split[0] +"and content_type="+split[1]); break;
                }
            }
        }}.toString();
        if (sql.toLowerCase().contains("where")) sql = sql + " AND " + MapperConstant.status;
        else sql = sql + " WHERE " + MapperConstant.status;
        return sql;
    }
}
