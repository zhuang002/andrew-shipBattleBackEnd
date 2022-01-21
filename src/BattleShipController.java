import java.util.List;

/***
 * This is the GUI controller for backend to call.
 * @author zhuan
 *
 */
public interface BattleShipController {
	void updateMyField(GridStatus[][] gridData);
	void updateRivalField(GridStatus[][] gridData);
	void gameFinished(int who); // 0 for people win, 1 for computer win.
	void setState(GuiState state);
}
