export interface LoginResponse {
  token: string;
  user: User;
}

export interface User {
  id: number;
  username: string;
  email: string;
  gender: string;
  isPublic: boolean;
  createdAt: string | Date;
  lastLogin: string | Date | null;
  totalPoints: number;
  totalActivities: number;
}
