/**
 * @Title: VUIAbstractFactory.java
 * @Package vEngine.ui
 * @Description: 用于创建UI的工厂类
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.ui;

/**
 * @author Demilichzz
 *
 */
public interface VUIAbstractFactory {
	public VUI creator(String type);
}
