package generate;

import generate.Dbinfo;

public interface DbinfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Dbinfo record);

    int insertSelective(Dbinfo record);

    Dbinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dbinfo record);

    int updateByPrimaryKey(Dbinfo record);
}