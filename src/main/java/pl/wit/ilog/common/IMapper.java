package pl.wit.ilog.common;

public interface IMapper<SRC,DST> {
    DST map(SRC src);
}
