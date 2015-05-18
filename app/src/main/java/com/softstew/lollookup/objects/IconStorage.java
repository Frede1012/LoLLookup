package com.softstew.lollookup.objects;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.softstew.lollookup.util.IconCacher;
import com.softstew.lollookup.util.IconLoader;

public class IconStorage implements Serializable {

	private static final long serialVersionUID = 1L;

	ArrayList<Icon> profileStorage = new ArrayList<Icon>();
	ArrayList<Icon> champStorage = new ArrayList<Icon>();

	public IconStorage() {

	}

	public IconStorage(ArrayList<Icon> profileStorage,
			ArrayList<Icon> champStorage) {
		this.profileStorage = profileStorage;
		this.champStorage = champStorage;
	}

	public IconStorage(ArrayList<Icon> profileIcons,
			ArrayList<Icon> champIcons, Context context, boolean forceLoad) {
		for (int i = 0; i < profileIcons.size(); i++) {
			int id = profileIcons.get(i).getID();
			if (forceLoad
					&& IconLoader.iconExists(context, profileIcons.get(i)
							.getType(), profileIcons.get(i).getID())) {
				profileStorage.add(IconCacher.loadCachedIcon(IconType.Profile,
						id, context));
			} else {
				profileStorage.add(IconLoader.getProfileIcon(context, id));
			}
		}
		for (int i = 0; i < champIcons.size(); i++) {
			int id = champIcons.get(i).getID();
			if (forceLoad
					&& IconLoader.iconExists(context, champIcons.get(i)
							.getType(), champIcons.get(i).getID())) {
				champStorage.add(IconCacher.loadCachedIcon(IconType.Profile,
						id, context));
			} else {
				champStorage.add(IconLoader.getChampionIcon(context, id));
			}
		}
	}

	public IconStorage(IconType type, ArrayList<Integer> ids, Context context) {
		add(type, ids, context);
	}

	public ArrayList<Integer> getIds(IconType type) {
		ArrayList<Icon> storage = getStorage(type);
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < storage.size(); i++) {
			ids.add(storage.get(i).getID());
		}
		return ids;
	}

	public Icon getIcon(IconType type, int id) {
		ArrayList<Icon> storage = getStorage(type);
		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).getID() == id) {
				return storage.get(i);
			}
		}
		return null;
	}

	public ArrayList<Icon> getStorage(IconType type) {
		if (type == IconType.Champion) {
			return champStorage;
		} else if (type == IconType.Profile) {
			return profileStorage;
		}
		return null;
	}

	public boolean isIconSaved(IconType type, int id) {
		ArrayList<Icon> storage = getStorage(type);
		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).getID() == id) {
				return true;
			}
		}
		return false;
	}

	public void add(ArrayList<Icon> icons, Context context) {
		for (int i = 0; i < icons.size(); i++) {
			if (isIconSaved(icons.get(i).getType(), icons.get(i).getID()) == false) {
				if (icons.get(i).getType() == IconType.Champion) {
					champStorage.add(IconLoader.getChampionIcon(context, icons
							.get(i).getID()));
				} else if (icons.get(i).getType() == IconType.Profile) {
					profileStorage.add(IconLoader.getProfileIcon(context, icons
							.get(i).getID()));
				}
			} else {
				if (icons.get(i).getType() == IconType.Champion) {
					champStorage.add(new Icon(false, icons.get(i).getType(),
							icons.get(i).getID()));
				} else if (icons.get(i).getType() == IconType.Profile) {
					profileStorage.add(new Icon(false, icons.get(i).getType(),
							icons.get(i).getID()));
				}
			}
		}
	}

	public void add(IconType type, ArrayList<Integer> ids, Context context) {
		for (int i = 0; i < ids.size(); i++) {
			if (isIconSaved(type, ids.get(i)) == false) {
				if (type == IconType.Champion) {
					champStorage.add(IconLoader.getChampionIcon(context,
							ids.get(i)));
				} else if (type == IconType.Profile) {
					profileStorage.add(IconLoader.getProfileIcon(context,
							ids.get(i)));
				}
			} else {
				if (type == IconType.Champion) {
					champStorage.add(new Icon(false, type, ids.get(i)));
				} else if (type == IconType.Profile) {
					profileStorage.add(new Icon(false, type, ids.get(i)));
				}
			}
		}
	}

	public void add(IconType type, int id, Context context) {
		if (isIconSaved(type, id) == false) {
			if (type == IconType.Champion) {
				champStorage.add(IconLoader.getChampionIcon(context, id));
			} else if (type == IconType.Profile) {
				profileStorage.add(IconLoader.getProfileIcon(context, id));
			}
		} else {
			if (type == IconType.Champion) {
				champStorage.add(new Icon(false, type, id));
			} else if (type == IconType.Profile) {
				profileStorage.add(new Icon(false, type, id));
			}
		}
	}

	public IconStorage getSerializeableStorage() {
		ArrayList<Icon> profileIcons = new ArrayList<Icon>();
		ArrayList<Icon> oldProfileIcons = getStorage(IconType.Profile);
		for (int i = 0; i < oldProfileIcons.size(); i++) {
			profileIcons.add(new Icon(oldProfileIcons.get(i).isPlaceholder(),
					IconType.Profile, oldProfileIcons.get(i).getID()));
		}
		ArrayList<Icon> champIcons = new ArrayList<Icon>();
		ArrayList<Icon> oldChampIcons = getStorage(IconType.Champion);
		for (int i = 0; i < oldChampIcons.size(); i++) {
			champIcons.add(new Icon(oldChampIcons.get(i).isPlaceholder(),
					IconType.Champion, oldChampIcons.get(i).getID()));
		}
		return new IconStorage(profileIcons, champIcons);
	}

}
