/**
 * 
 */
package ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年3月1日 上午9:54:22 
* 类说明 :
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface CacheResult {
	/** 
	* @author  zhangjian
	* @version JDK1.8
	* 创建时间：2018年3月1日 上午9:54:22 
	* 方法说明 :
	*/
	String key() ;
}
