package xiaogao.zjut.tabbaishuo.main.activity.im.tool.extension;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.LocationPlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/12/1.
 */

public class MyExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> ls = super.getPluginModules(conversationType);
        ls.add(new LocationPlugin());
        return ls;
    }
}
