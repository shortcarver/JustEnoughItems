package mezz.jei.gui.search;

import mezz.jei.core.search.PrefixInfo;
import mezz.jei.gui.ingredients.IListElement;
import mezz.jei.gui.ingredients.IListElementInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementSearchLowMem implements IElementSearch {
	private static final Logger LOGGER = LogManager.getLogger();

	private final List<IListElementInfo<?>> elementInfoList;

	public ElementSearchLowMem() {
		this.elementInfoList = new ArrayList<>();
	}

	@Override
	public Set<IListElement<?>> getSearchResults(ElementPrefixParser.TokenInfo tokenInfo) {
		String token = tokenInfo.token();
		if (token.isEmpty()) {
			return Set.of();
		}

		PrefixInfo<IListElementInfo<?>, IListElement<?>> prefixInfo = tokenInfo.prefixInfo();
		return this.elementInfoList.stream()
			.filter(elementInfo -> matches(token, prefixInfo, elementInfo))
			.map(IListElementInfo::getElement)
			.collect(Collectors.toSet());
	}

	private static boolean matches(String word, PrefixInfo<IListElementInfo<?>, IListElement<?>> prefixInfo, IListElementInfo<?> elementInfo) {
		IListElement<?> element = elementInfo.getElement();
		if (element.isVisible()) {
			Collection<String> strings = prefixInfo.getStrings(elementInfo);
			for (String string : strings) {
				if (string.contains(word)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void add(IListElementInfo<?> info) {
		this.elementInfoList.add(info);
	}

	@Override
	public void addAll(Collection<IListElementInfo<?>> infos) {
		this.elementInfoList.addAll(infos);
	}

	@Override
	public List<IListElement<?>> getAllIngredients() {
		return this.elementInfoList.stream()
			.<IListElement<?>>map(IListElementInfo::getElement)
			.toList();
	}

	@Override
	public void logStatistics() {
		LOGGER.info("ElementSearchLowMem Element Count: {}", this.elementInfoList.size());
	}
}
