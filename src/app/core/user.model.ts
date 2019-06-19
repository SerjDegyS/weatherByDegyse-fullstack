export interface IFavCity {
  id: number;
  name: string;
}

export interface IUser {
  uid: string;
  email?: string | null;
  photoURL?: string;
  displayName?: string;
  favCity?: IFavCity[];
}
