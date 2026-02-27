export interface UserRegisterDTO {
  username: string;
  password: string;
  email: string;
  gender: string;
  isPublic?: boolean;
}
