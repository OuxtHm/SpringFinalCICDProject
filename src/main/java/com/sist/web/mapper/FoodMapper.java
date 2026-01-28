package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.FoodVO;

@Mapper
@Repository
public interface FoodMapper {
	@Select("SELECT fno,name,poster "
			   +"FROM menupan_food "
			   +"ORDER BY fno ASC "
			   +"OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
		public List<FoodVO> foodListData(int start);
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM menupan_food")
	public int foodTotalPage();
	

	@Select("SELECT fno,name,poster "
			   +"FROM (SELECT fno,name,poster "
			   +"FROM menupan_food "
			   +"WHERE address LIKE '%'||#{address}||'%' "
			   +"ORDER BY fno ASC) "
			   +"OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<FoodVO> foodFindData(@Param("start") int start,@Param("address") String address );
	
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM menupan_food "
		   +"WHERE address LIKE '%'||#{address}||'%'")
	public int foodFindTotalPage(String address);
	
	// 상세보기 
	@Update("UPDATE menupan_food SET "
		   +"hit=hit+1 "
		   +"WHERE fno=#{fno}")
	public void foodHitIncrement(int fno);
	
	@Select("SELECT fno,name,poster,address,phone,type,"
		    +"time,parking,score,theme, content, price "
			+"FROM menupan_food "
		    +"WHERE fno=#{fno}")
	public FoodVO foodDetailData(int fno);
}
