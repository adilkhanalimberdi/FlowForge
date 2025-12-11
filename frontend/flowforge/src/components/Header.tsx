import type {User} from "../types/User.ts";

interface Props {
    user: User;
}


function Header({user}: Props) {
    return (
        <header className="flex h-[70px] items-center justify-between border-b-1 border-gray-600">
            <div className="logo pl-10 pr-10">
                <h1 className="text-3xl text-gray-600 font-bold">
                    <span className="text-violet-800">F</span>low
                    <span className="text-violet-800">F</span>orge
                </h1>
            </div>

            <div className="right-side flex gap-8 pr-10">
                <div className="search-bar w-[700px]">
                    <input className="w-full h-[50px] pl-3 rounded-xl border-1 border-gray-600" type="text" placeholder="Search..."/>
                </div>

                <div className="profile">
                    <img src={user.profilePictureUrl} alt="" className="profile-pic w-[50px] h-[50px] bg-cover rounded-full border object-cover" />
                </div>
            </div>
        </header>
    )
}

export default Header;