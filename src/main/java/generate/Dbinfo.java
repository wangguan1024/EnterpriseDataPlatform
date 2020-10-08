package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * dbinfo
 * @author 
 */
@Data
public class Dbinfo implements Serializable {
    private Integer id;

    private String url;

    private String username;

    private String password;

    private String dbname;

    private String tableStr;

    private static final long serialVersionUID = 1L;
}