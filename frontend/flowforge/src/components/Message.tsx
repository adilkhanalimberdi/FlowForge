import {useEffect} from "react";

interface Props {
    message: string;
    onClose: () => void;
}

function Message({message, onClose}: Props) {
    useEffect(() => {
        const timer = setTimeout(() => {
            onClose();
        }, 2000);

        return () => clearTimeout(timer);
    }, [onClose]);

    return (
        <div className="fixed flex items-end justify-end w-full h-full inset-0 select-none">
            <div className="w-[400px] h-[15%] flex items-center justify-center bg-cyan-300 rounded-2xl">
                <img src="../assets/check-black.png" className="border object-cover" alt="blya"/>
                <h1 className="border text-center align-center text-xl">
                    {message}
                </h1>
            </div>
        </div>
    )
}

export default Message;