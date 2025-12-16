import type {User} from "../types/User.ts";
import logo from "../assets/logo.png";

interface Props {
    user: User;
}


function getLength(lastname: string) {
    if (lastname === null || lastname.length === 0) return 1;
    return 2;
}


function Header({user}: Props) {
    console.log(user);
    return (
        <header className="flex h-[70px] items-center justify-between border-b-1 border-gray-600 pl-30 pr-30">
            <div className="logo flex items-center justify-center">
                <img src={logo} alt="logo" className="w-[40px] h-[40px] scale-200 mr-2"/>
                {/*<h1 className="font-bold text-2xl text-gray-500">*/}
                {/*    <span className="text-blueviolet-500 ">F</span>low*/}
                {/*    <span className="text-[#9DBCF9]">F</span>orge*/}
                {/*</h1>*/}
            </div>

            <div className="right-side flex gap-8 h-[50px]">
                <div className="search-bar w-[700px]">
                    <input className="w-full h-[50px] pl-3 rounded-xl border-1 border-gray-500" type="text" placeholder="Search..."/>
                </div>

                <div className="profile">
                    <img src={
                        (!user.profilePictureUrl ?
                            ( "https://ui-avatars.com/api/?" +
                                "name=" + user.firstName + "+" + user.lastName + "&" +
                                "background=d4d5d9&" +
                                "color=6f737d&" +
                                "size=128&" +
                                "length=" + getLength(user.lastName) + "&" +
                                "rounded=false&" +
                                "font-size=0.4&" +
                                "bold=true&" +
                                "uppercase=true&" +
                                "format=png") :
                            user.profilePictureUrl)
                    } alt="" className="profile-pic w-[50px] h-[50px] bg-cover rounded-full object-cover block" />
                </div>
            </div>
        </header>
    )
}

export default Header;